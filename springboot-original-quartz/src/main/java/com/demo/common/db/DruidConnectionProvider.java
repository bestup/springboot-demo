package com.demo.common.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.quartz.SchedulerException;
import org.quartz.utils.ConnectionProvider;

import com.alibaba.druid.pool.DruidDataSource;

/**
 *  Druid连接池的Quartz扩展类
 *  
 *  Quartz 2.0 以前 DBCP 
 *  Quartz 2.0 以后 C3P0（包含2.0）
 *  
 *  在quartz.properties文件中的配置项： 
 *  org.quartz.dataSource.myDS(数据源名).connectionProvider.class:org.quartz.utils.PoolingConnectionProvider
 *  
 *  扩展Druid数据库连接池配置调整如下 
 *  org.quartz.dataSource.myDS(数据源名).connectionProvider.class = XXXXX(自定义的ConnectionProvider)
 */


public class DruidConnectionProvider implements ConnectionProvider{
	
    private String driver;
    private String URL;
    private String user;
    private String password;
    private int maxConnection;	//数据库最大连接数
    
    //数据库SQL查询每次连接返回执行到连接池，以确保它仍然是有效的。
    public String validationQuery;
    private boolean validateOnCheckout;
    private int idleConnectionValidationSeconds;
    public String maxCachedStatementsPerConnection;
    private String discardIdleConnectionsSeconds;
    public static final int DEFAULT_DB_MAX_CONNECTIONS = 10;
    public static final int DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = 120;
    
    //Druid连接池
    private DruidDataSource datasource;
    
    

	@Override
	public Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	@Override
	public void shutdown() throws SQLException {
		datasource.close();
	}

	@Override
	public void initialize() throws SQLException {
		 if (this.URL == null) {
			 throw new SQLException("DBPool could not be created: DB URL cannot be null");
	     }
        if (this.driver == null) {
            throw new SQLException("DBPool driver could not be created: DB driver class name cannot be null!");
        }
        if (this.maxConnection < 0) {
            throw new SQLException("DBPool maxConnectins could not be created: Max connections must be greater than zero!");
        }
        datasource = new DruidDataSource();
        try{
            datasource.setDriverClassName(this.driver);
        } catch (Exception e) {
            try {
                throw new SchedulerException("Problem setting driver class name on datasource: " + e.getMessage(), e);
            } catch (SchedulerException e1) {
            }
        }
        datasource.setUrl(this.URL);
        datasource.setUsername(this.user);
        datasource.setPassword(this.password);
        datasource.setMaxActive(this.maxConnection);
        datasource.setMinIdle(1);
        datasource.setMaxWait(0);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(DEFAULT_DB_MAX_CONNECTIONS);
        if (this.validationQuery != null) {
            datasource.setValidationQuery(this.validationQuery);
            if(!this.validateOnCheckout) {
            	datasource.setTestOnReturn(true);
            }else {
            	datasource.setTestOnBorrow(true);
                datasource.setValidationQueryTimeout(this.idleConnectionValidationSeconds);
            }
        }
	}
	
	
	/**
	 * set/get方法
	 */
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxConnection() {
		return maxConnection;
	}

	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isValidateOnCheckout() {
		return validateOnCheckout;
	}

	public void setValidateOnCheckout(boolean validateOnCheckout) {
		this.validateOnCheckout = validateOnCheckout;
	}

	public int getIdleConnectionValidationSeconds() {
		return idleConnectionValidationSeconds;
	}

	public void setIdleConnectionValidationSeconds(int idleConnectionValidationSeconds) {
		this.idleConnectionValidationSeconds = idleConnectionValidationSeconds;
	}

	public String getMaxCachedStatementsPerConnection() {
		return maxCachedStatementsPerConnection;
	}

	public void setMaxCachedStatementsPerConnection(String maxCachedStatementsPerConnection) {
		this.maxCachedStatementsPerConnection = maxCachedStatementsPerConnection;
	}

	public String getDiscardIdleConnectionsSeconds() {
		return discardIdleConnectionsSeconds;
	}

	public void setDiscardIdleConnectionsSeconds(String discardIdleConnectionsSeconds) {
		this.discardIdleConnectionsSeconds = discardIdleConnectionsSeconds;
	}

	public DruidDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DruidDataSource datasource) {
		this.datasource = datasource;
	}
	
}
