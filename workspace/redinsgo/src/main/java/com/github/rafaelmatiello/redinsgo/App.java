package com.github.rafaelmatiello.redinsgo;

/**
 * Bingo
 *
 */
public class App {
	
	
	public static void main(String[] args) {
		Redinsgo bingo = new Redinsgo();
		bingo.iniciar();
		
		while(!bingo.temGanhador()) {
			bingo.sortear();
		}
		
		bingo.mostrarGanhadores();
	}
}
