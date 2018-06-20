package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

public class RedisSentinelConnectionFactory implements InitializingBean, DisposableBean, RedisConnectionFactory {
	private static final Logger logger = LoggerFactory.getLogger(RedisSentinelConnectionFactory.class);

	private JedisShardInfo shardInfo;
	private String hostName = "localhost";
	private int port = Protocol.DEFAULT_PORT;
	private int timeout = Protocol.DEFAULT_TIMEOUT;
	private String password;
	private boolean usePool = false;	
	private Pool<Jedis> pool;
	private JedisSentinelPool sentinelPool;
	
	private JedisPoolConfig poolConfig = new JedisPoolConfig();
	private int dbIndex = 0;
	private boolean convertPipelineAndTxResults = true;
	
	/**
	 * Constructs a new <code>JedisConnectionFactory</code> instance with default settings (default connection pooling, no
	 * shard information).
	 */
	public RedisSentinelConnectionFactory() {
	}
	
	/**
	 * Constructs a new <code>JedisConnectionFactory</code> instance. Will override the other connection parameters passed
	 * to the factory.
	 * 
	 * @param shardInfo shard information
	 */
	public RedisSentinelConnectionFactory(JedisShardInfo shardInfo){
		this.shardInfo = shardInfo;
	}
	
	/**
	 * Constructs a new <code>JedisConnectionFactory</code> instance using the given pool configuration.
	 * 
	 * @param poolConfig pool configuration
	 */
	public RedisSentinelConnectionFactory(JedisPoolConfig poolConfig){
		this.poolConfig = poolConfig;
	}
	
	/**
	 * Returns a Jedis instance to be used as a Redis connection. The instance can be newly created or retrieved from a
	 * pool.
	 * 
	 * @return Jedis instance ready for wrapping into a {@link RedisConnection}.
	 */
	public Jedis fetchJedisConnector() {
		try {
			if (usePool && pool != null) {
				return pool.getResource();
			}
			Jedis jedis = new Jedis(getShardInfo());
			// force initialization (see Jedis issue #82)
			jedis.connect();
			return jedis;
		} catch (Exception ex) {
			
			throw new RedisConnectionFailureException("Cannot get Jedis connection", ex);
		}
	}
	
	/**
	 * Post process a newly retrieved connection. Useful for decorating or executing initialization commands on a new
	 * connection. This implementation simply returns the connection.
	 * 
	 * @param connection
	 * @return processed connection
	 */
	protected JedisConnection postProcessConnection(JedisConnection connection) {
		return connection;
	}
	
	@Override
	public DataAccessException translateExceptionIfPossible(
			RuntimeException arg0) {
		return JedisConverters.toDataAccessException(arg0);
	}

	@Override
	public RedisConnection getConnection() {
		Jedis jedis = fetchJedisConnector();
		JedisConnection connection = (usePool ? new JedisConnection(jedis, pool, dbIndex) : new JedisConnection(jedis,
				null, dbIndex));
		connection.setConvertPipelineAndTxResults(convertPipelineAndTxResults);
		return postProcessConnection(connection);
	}
	
	/**
	 * Specifies if pipelined results should be converted to the expected data type. If false, results of
	 * {@link JedisConnection#closePipeline()} and {@link JedisConnection#exec()} will be of the type returned by the
	 * Jedis driver
	 * 
	 * @return Whether or not to convert pipeline and tx results
	 */
	@Override
	public boolean getConvertPipelineAndTxResults() {
		return convertPipelineAndTxResults;
	}

	/**
	 * Specifies if pipelined results should be converted to the expected data type. If false, results of
	 * {@link JedisConnection#closePipeline()} and {@link JedisConnection#exec()} will be of the type returned by the
	 * Jedis driver
	 * 
	 * @param convertPipelineAndTxResults Whether or not to convert pipeline and tx results
	 */
	public void setConvertPipelineAndTxResults(boolean convertPipelineAndTxResults) {
		this.convertPipelineAndTxResults = convertPipelineAndTxResults;
	}


	@Override
	public void destroy() throws Exception {
		if (usePool && pool != null) {
			try {
				pool.destroy();
			} catch (Exception ex) {
				logger.warn("Cannot properly close Jedis pool", ex);
			}
			pool = null;
		}
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (shardInfo == null) {
			shardInfo = new JedisShardInfo(hostName, port);

			if (StringUtils.hasLength(password)) {
				shardInfo.setPassword(password);
			}

			if (timeout > 0) {
				shardInfo.setConnectionTimeout(timeout);
				shardInfo.setSoTimeout(timeout);
			}
		}

		
		if (usePool == true && pool == null) {
			HostAndPort host = sentinelPool.getCurrentHostMaster();
			pool = new JedisPool(poolConfig, host.getHost(), host.getPort(), shardInfo.getConnectionTimeout(),
					shardInfo.getPassword());
		}
	}

	/**
	 * Returns the Redis hostName.
	 * 
	 * @return Returns the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Sets the Redis hostName.
	 * 
	 * @param hostName The hostName to set.
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * Returns the password used for authenticating with the Redis server.
	 * 
	 * @return password for authentication
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password used for authenticating with the Redis server.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the port used to connect to the Redis instance.
	 * 
	 * @return Redis port.
	 */
	public int getPort() {
		return port;

	}

	/**
	 * Sets the port used to connect to the Redis instance.
	 * 
	 * @param port Redis port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Returns the shardInfo.
	 * 
	 * @return Returns the shardInfo
	 */
	public JedisShardInfo getShardInfo() {
		return shardInfo;
	}

	/**
	 * Sets the shard info for this factory.
	 * 
	 * @param shardInfo The shardInfo to set.
	 */
	public void setShardInfo(JedisShardInfo shardInfo) {
		this.shardInfo = shardInfo;
	}
	/*
	public JedisSentinelPool getSentinelPool() {
		return sentinelPool;
	}
	*/
	public void setSentinelPool(JedisSentinelPool pool) {
		this.pool = pool;
	}

	/**
	 * Returns the timeout.
	 * 
	 * @return Returns the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout The timeout to set.
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * Indicates the use of a connection pool.
	 * 
	 * @return Returns the use of connection pooling.
	 */
	public boolean getUsePool() {
		return usePool;
	}

	/**
	 * Turns on or off the use of connection pooling.
	 * 
	 * @param usePool The usePool to set.
	 */
	public void setUsePool(boolean usePool) {
		this.usePool = usePool;
	}

	/**
	 * Returns the poolConfig.
	 * 
	 * @return Returns the poolConfig
	 */
	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	/**
	 * Sets the pool configuration for this factory.
	 * 
	 * @param poolConfig The poolConfig to set.
	 */
	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	/**
	 * Returns the index of the database.
	 * 
	 * @return Returns the database index
	 */
	public int getDatabase() {
		return dbIndex;
	}

	/**
	 * Sets the index of the database used by this connection factory. Default is 0.
	 * 
	 * @param index database index
	 */
	public void setDatabase(int index) {
		Assert.isTrue(index >= 0, "invalid DB index (a positive index required)");
		this.dbIndex = index;
	}

	@Override
	public RedisSentinelConnection getSentinelConnection() {
		return null;
	}
}
