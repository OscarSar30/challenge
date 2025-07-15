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