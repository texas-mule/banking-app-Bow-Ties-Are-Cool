CREATE TABLE public.useraccount (
	username varchar NOT NULL,
	"password" varchar NOT NULL,
	customer bool NOT NULL,
	employee bool NOT NULL,
	"admin" bool NOT NULL,
	CONSTRAINT useraccount_pk PRIMARY KEY (username),
	CONSTRAINT username_un UNIQUE (username)
);
