package br.com.projeto.concorrente;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ProgramaConcorrente {

	private static HttpRequest request;
	
	public static void main(String[] args) {
		
		request = HttpRequest.newBuilder()
			.uri(URI.create("https://roll-dice1.p.rapidapi.com/rollDice"))
			.header("X-RapidAPI-Key", "ff3cab6d88mshe66c9d082380c11p1af4e7jsn932b68b52131")
			.header("X-RapidAPI-Host", "roll-dice1.p.rapidapi.com")
			.method("GET", HttpRequest.BodyPublishers.noBody())
			.build();
	
		int qtdExecucoes = 450;
		
		ArrayList<Thread> listaThreadsExecultadas = new ArrayList<>();
		
		//Criando as threads
		for (int i = 0; i < qtdExecucoes ; i++) {
			Thread thr = new Thread(thread);
			listaThreadsExecultadas.add(thr);
		}
		
		long tempoInicial = System.currentTimeMillis();
		
		for(Thread thr : listaThreadsExecultadas) {
			thr.start();
		}
		
		for(Thread thr : listaThreadsExecultadas) {
			try {
				thr.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long tempoFinal = System.currentTimeMillis() - tempoInicial;
		
		System.out.println("Fim do Programa");
		System.out.println("Tempo gasto: " + tempoFinal + " milisegundos");
		System.out.println("Execuçoes: " + qtdExecucoes);
	}
	
	/*
	 * Thread que chama a API roll_dice e retorna o resultado da requisicao
	 */
	private static Runnable thread = new Runnable() {
		public void run() {
			try {
				
				
				HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				
				System.out.println("Resultado API: (" + response.body() + ")");
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	};
	
}
