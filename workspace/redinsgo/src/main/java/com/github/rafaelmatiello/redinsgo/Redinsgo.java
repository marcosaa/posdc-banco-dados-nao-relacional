package com.github.rafaelmatiello.redinsgo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class Redinsgo {

	private static final String CHAVE_Z_SCORES = "zScores";
	private static final String CHAVE_SCORE = "score:";
	private static final String CHAVE_NUMEROS_DISPONIVEIS = "sNumerosDisponiveis";
	private static final short QUANTIDADE_NUMERO_CARTELA = 15;
	private static final String CHAVE_CARTELA = "cartela:";
	private static final String CHAVE_USER = "user:";
	private static final String CHAVE_USER_NAME = "name";
	private static final String CHAVE_USER_CARTELA = "bcartela:";
	private static final String CHAVE_USER_SCORE = "bscore";
	private static final short QUANTIDADE_JOGADORES = 50;
	private static final String CHAVE_SET_SCORE = "bscores:";
	private static final String CHAVE_BOLAS = "sBolas";
	private static final String CHAVE_BOLAS_SORTEADAS = "sBolasSorteadas";
	private boolean temGanhador = false;
	private Set<String> listaGanhadores = new HashSet<String>();

	public void criarNumerosCartelaBolas() {
		Jedis connect = RedisConnection.connect();
		for (int i = 1; i <= 99; i++) {
			connect.sadd(CHAVE_NUMEROS_DISPONIVEIS, String.valueOf(i));
			connect.sadd(CHAVE_BOLAS, String.valueOf(i));
		}
	}

	public void criarJogadores(short quantidadeJogadores) {
		Jedis connect = RedisConnection.connect();
		for (int i = 1; i <= quantidadeJogadores; i++) {
			String CHAVE_USUARIO_ATUAL = CHAVE_USER + i;
			connect.hset(CHAVE_USUARIO_ATUAL, CHAVE_USER_NAME, CHAVE_USER + i);
			connect.hset(CHAVE_USUARIO_ATUAL, CHAVE_USER_CARTELA, CHAVE_CARTELA + i);
			connect.hset(CHAVE_USUARIO_ATUAL, CHAVE_USER_SCORE, CHAVE_SCORE + i);
		}
	}

	public void criarCartelas(short quantidadeJogadores) {
		Jedis connect = RedisConnection.connect();
		for (int i = 1; i <= quantidadeJogadores; i++) {
			List<String> srandmember = connect.srandmember(CHAVE_NUMEROS_DISPONIVEIS, QUANTIDADE_NUMERO_CARTELA);
			for (String numeroSorteado : srandmember) {
				connect.sadd(CHAVE_CARTELA + i, numeroSorteado);
			}
		}
	}

	public void criarScore(short quantidadeJogadores) {
		Jedis connect = RedisConnection.connect();
		for (int i = 1; i <= quantidadeJogadores; i++) {
			connect.zadd(CHAVE_Z_SCORES, 0, CHAVE_SET_SCORE + i);
		}
	}

	public void iniciar() {
		this.limparBanco();
		this.criarNumerosCartelaBolas();
		this.criarCartelas(QUANTIDADE_JOGADORES);
		this.criarJogadores(QUANTIDADE_JOGADORES);
		this.criarScore(QUANTIDADE_JOGADORES);
	}

	private void limparBanco() {
		RedisConnection.connect().flushAll();
	}

	public boolean temGanhador() {
		return temGanhador;
	}

	public void sortear() {
		Jedis connect = RedisConnection.connect();
		String bolaSorteada = connect.srandmember(CHAVE_BOLAS);
		connect.srem(CHAVE_BOLAS, bolaSorteada);
		connect.sadd(CHAVE_BOLAS_SORTEADAS, bolaSorteada);

		for (int i = 1; i <= QUANTIDADE_JOGADORES; i++) {
			Boolean encontrou = connect.sismember(CHAVE_CARTELA + i, bolaSorteada);
			if (encontrou) {
				connect.zincrby(CHAVE_Z_SCORES, 1.0, CHAVE_SET_SCORE + i);
			}
		}

		checkGanhadores(connect);

	}

	private void checkGanhadores(Jedis connect) {
		Set<Tuple> zrevrangeWithScores = connect.zrevrangeWithScores(CHAVE_Z_SCORES, 0, 10);
		for (Tuple tuple : zrevrangeWithScores) {
			if (tuple.getScore() == 15.0) {
				temGanhador = true;
				listaGanhadores.add(tuple.getElement().replace(CHAVE_SET_SCORE, CHAVE_USER));
			}
		}
	}

	public void mostrarGanhadores() {

		Jedis connect = RedisConnection.connect();

		System.out.println("Ganhadores:");
		for (String ganhador : listaGanhadores) {
			System.out.println(ganhador);
			imprimirCartela(connect, ganhador);
		}
		imprimirBolarSorteadas(connect);
	}

	private void imprimirBolarSorteadas(Jedis connect) {
		Set<String> smembers = connect.smembers(CHAVE_BOLAS_SORTEADAS);
		StringBuilder sb = new StringBuilder();
		for (String bola : smembers) {
			sb.append(bola).append(" ");
		}

		System.out.println("Bolar sorteadas (" + smembers.size() + "): " + sb.toString());
	}

	private void imprimirCartela(Jedis connect, String ganhador) {
		String codigoGanhador = ganhador.substring(ganhador.indexOf(":") + 1);
		Set<String> numeroCartela = connect.smembers(CHAVE_CARTELA + codigoGanhador);
		StringBuilder sb = new StringBuilder();
		for (String numero : numeroCartela) {
			sb.append(numero).append(" ");
		}
		System.out.println("Cartela: " + sb.toString());
	}

}
