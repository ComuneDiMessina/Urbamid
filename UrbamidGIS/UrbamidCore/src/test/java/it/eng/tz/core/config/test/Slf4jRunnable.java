package it.eng.tz.core.config.test;

public class Slf4jRunnable implements Runnable {
	private final Transfer tx;

	public Slf4jRunnable(Transfer tx) {
		this.tx = tx;
	}

	public void run() {

//		LogUtil.initLogParameter(tx.getSender(), tx.getTransactionId());
//
//		new Slf4TransferService().transfer(tx.getAmount());
//
//		LogUtil.endLogParameter();
	}
}