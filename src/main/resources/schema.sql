DROP TABLE IF EXISTS "books";
DROP TABLE IF EXISTS "authors";

CREATE TABLE "authors" (
    "id" biginit DEFAULT nextval('authors_id_seq') NOT NULL,
    "name" text,
    "age" integer
    CONSTRAINT "authors_pkey" PRIMARY KEY ("id")
    );

CREATE TABLE "books" (
    "isbn" text NOT NULL,
    "title" text,
    "author_id" biginit,
    CONSTRAINT "books_pkey" PRIMARY KEY ("isbn"),
    CONSTRAINT "authors_fk" FOREIGN KEY ("author_id")
    REFERENCES authors(id)
    );