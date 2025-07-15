-- Drop table

-- DROP TABLE public.person;

CREATE TABLE IF NOT EXISTS public.person (
	person_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	address varchar(255) NULL,
	age int4 NULL,
	gender varchar(255) NULL,
	identification varchar(255) NULL,
	full_name varchar(255) NULL,
	phone varchar(255) NULL,
	CONSTRAINT uk_r5vsms84ih2viwd6tatk9o5pr UNIQUE (identification)
);


-- Drop table

-- DROP TABLE public.customer;

CREATE TABLE IF NOT EXISTS public.customer (
	customer_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	password varchar(255) NULL,
	status bool NULL,
	person_id UUID NOT NULL,
	CONSTRAINT fkgdh25m0u6r4xr9iuqnsbbh12q FOREIGN KEY (person_id) REFERENCES public.person(person_id)
);

-- Drop table

-- DROP TABLE public.accounts;

CREATE TABLE IF NOT EXISTS public.accounts (
	account_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
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
	movement_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
	date_movement timestamp NOT NULL,
	balance numeric(15, 2) NOT NULL,
	movement_type varchar(255) NULL,
	amount float8 NOT NULL,
	account_id UUID NOT NULL,
	CONSTRAINT fk7dg9eam32ow23n4moo6x5oo6e FOREIGN KEY (account_id) REFERENCES public.accounts(account_id)
);