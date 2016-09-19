package com.cpcb.gs.io;

import java.lang.Iterable;
import java.util.Iterator;

public class TableT<T extends BaseDeploy> implements Iterable<T> {

	public T getDeploy(int id) {
		return null;
	}

	public Iterator<T> iterator() {
		return null;
	}

	private static class MyInterator<T> implements Iterator<T> {
		private int currIndex;

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public T next() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
