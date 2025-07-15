-- Drop table

-- DROP TABLE public.accounts;

CREATE TABLE IF NOT EXISTS public.accounts (
	account_id int4 PRIMARY KEY DEFAULT uuid_generate_v4(),
	status bool NULL,
	account_number varchar(255) NULL,
	initial_balance float8 NULL,
	account_type varchar(255) NULL,
	customer_id UUID NOT NULL,
	CONSTRAINT accounts_un UNIQUE (account_number),
	CONSTRAINT fk65yk2321jpusl3fk96lqehrlj FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id)
);


-- Drop table

-- DROP TABLE public.movements;

CREATE TABLE IF NOT EXISTS public.movements(
	movement_id int4 PRIMARY KEY DEFAULT uuid_generate_v4(),
	date_movement timestamp NOT NULL,
	balance numeric(15, 2) NOT NULL,
	movement_type varchar(255) NULL,
	amount float8 NOT NULL,
	account_id UUID NOT NULL,
	CONSTRAINT fk7dg9eam32ow23n4moo6x5oo6e FOREIGN KEY (account_id) REFERENCES public.accounts(account_id)
);