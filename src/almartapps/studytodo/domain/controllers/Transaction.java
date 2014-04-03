package almartapps.studytodo.domain.controllers;

import android.content.Context;

public abstract class Transaction {

	protected Context context;
	
	public Transaction(Context context) {
		this.context = context;
	}
	
}
