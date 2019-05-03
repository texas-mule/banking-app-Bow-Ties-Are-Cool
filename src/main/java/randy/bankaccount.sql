CREATE TABLE public.bankaccount (
	accountnumber serial NOT NULL,
	mainsigner varchar NOT NULL,
	cosigner varchar NULL,
	status int4 NOT NULL,
	balance numeric(32,2) NOT NULL DEFAULT 0.00,
	CONSTRAINT bankaccount_pk PRIMARY KEY (accountnumber),
	CONSTRAINT bankaccount_un UNIQUE (mainsigner, cosigner),
	CONSTRAINT bankaccount_useraccount_fk FOREIGN KEY (mainsigner) REFERENCES useraccount(username) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT bankaccount_useraccount_fk_1 FOREIGN KEY (cosigner) REFERENCES useraccount(username) ON UPDATE CASCADE ON DELETE CASCADE
);
