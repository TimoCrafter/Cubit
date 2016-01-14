package de.keks.internal.core.database.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class SQLConnectionManager {

	public final static SQLConnectionManager DEFAULT = new SQLConnectionManager();

	private final Map<String, SQLConnectionHandler> map;

	public SQLConnectionHandler getHandler(String key, SQLConnectionFactory f) {
		SQLConnectionHandler handler = new SQLConnectionHandler(key, f);
		map.put(key, handler);
		return handler;
	}

	public Connection getConnection(String handle) throws SQLException {
		return map.get(handle).getConnection();
	}

	public void release(String handle, Connection c) {
		map.get(handle).release(c);
	}

	public SQLConnectionHandler getHandler(String key) {
		SQLConnectionHandler handler = map.get(key);
		if (handler == null) {
			throw new NoSuchElementException();
		}
		return handler;
	}

	public SQLConnectionManager() {
		this.map = new ConcurrentHashMap<>();
	}

	public void shutdown() {
		for (SQLConnectionHandler handler : map.values()) {
			handler.shutdown();
		}
		map.clear();
	}
}