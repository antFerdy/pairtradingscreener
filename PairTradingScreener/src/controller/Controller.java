package controller;


import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import util.QuoteLoader;
import util.SpreadCounter;
import util.UtilHelper;
import view.App;
import model.Portfolio;
import model.SpreadObject;
import model.Stock;

public class Controller {
	private static App inst;
	private static QuoteLoader loader;
	private static Portfolio p;
	private static List<String> tickersToDelete = new ArrayList<String>();
	
	public static void main(String[] args) {
		loader = new QuoteLoader();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inst = new App();
				inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				
				
			}
		});
	}
		
	public static void getQuotes(String [] tickers) throws CloneNotSupportedException {
		for(String ticker : tickers) {
			loader.loadQuotes(ticker);
		}
		p = loader.getPortfolio();
		
		countMath();
//		for(String item : tickersToDelete) {
//			p.delete(item);
//		}
		
		//������� �����
		SpreadCounter spCounter = new SpreadCounter();
		List<SpreadObject> list = spCounter.loadSpread(p);
		
		//��������� ������ � ������� � �������
		inst.addData(list);

	}

	private static void countMath() {
		//����������� ����� � ������ �� ���, ������� ��� ������� ��� ����������
		Iterator<Stock> it = p.getWatchList().iterator();
		while(it.hasNext()) {
			String ticker = UtilHelper.loadMath(it.next());
			if(ticker != null) {
				it.remove();
			}
		}
	}
	
	public static void clearPortfolio() {
		p.clear();
	}
	

}
  