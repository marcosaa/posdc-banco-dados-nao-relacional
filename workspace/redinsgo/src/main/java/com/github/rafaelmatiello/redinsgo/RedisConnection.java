package com.github.rafaelmatiello.redinsgo;

import redis.clients.jedis.Jedis;

public class RedisConnection {

	private static Jedis JEDIS;

	public static Jedis connect() {
		if (JEDIS == null) {
			JEDIS = new Jedis();
		}
		return JEDIS;
	}
}
