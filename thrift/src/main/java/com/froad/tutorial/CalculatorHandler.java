package com.froad.tutorial;

import org.apache.thrift.TException;

import com.froad.shared.SharedStruct;

public class CalculatorHandler implements Calculator.Iface{

	@Override
	public SharedStruct getStruct(int key) throws TException {
		SharedStruct ss = new SharedStruct();
		ss.key = key;
		ss.setValue("SharedStruct");
		return ss;
	}

	@Override
	public void ping() throws TException {
		System.out.println("ping");
	}

	@Override
	public int add(int num1, int num2) throws TException {
		// TODO Auto-generated method stub
		return num1 + num2;
	}

	@Override
	public int calculate(int logid, Work w) throws InvalidOperation, TException {
		System.out.println(logid);
		switch(w.op) {
		case ADD:
			return w.num1 + w.num2;
		case SUBTRACT:
			return w.num1 - w.num2;
		case MULTIPLY:
			return w.num1 * w.num2;
		case DIVIDE:
			return w.num1 / w.num2;
		default:
			throw new InvalidOperation();
		}
	}

	@Override
	public void zip() throws TException {
		// TODO Auto-generated method stub
		
	}

}
