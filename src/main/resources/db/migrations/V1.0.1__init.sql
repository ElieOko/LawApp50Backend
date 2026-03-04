SET search_path TO public;

CREATE TABLE IF NOT EXISTS public.users (
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(100),
    password TEXT,
    email VARCHAR(150) UNIQUE,
    pseudo VARCHAR(100),
    is_active BOOLEAN DEFAULT FALSE,
    is_premium BOOLEAN DEFAULT FALSE,
    is_certified BOOLEAN DEFAULT FALSE,
    is_lock BOOLEAN DEFAULT FALSE,
    images TEXT,
    phone VARCHAR(30),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS public.refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    expires_at TIMESTAMP,
    hashed_token TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.type_accounts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.teacher_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.type_account_users (
    id BIGSERIAL PRIMARY KEY,
    type_account_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_tau_type_account
        FOREIGN KEY (type_account_id)
        REFERENCES public.type_accounts(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_tau_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_type_account_user
        UNIQUE (type_account_id, user_id)
);

CREATE TABLE IF NOT EXISTS public.accounts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type_account_id BIGINT NOT NULL,
    CONSTRAINT fk_account_type_account
        FOREIGN KEY (type_account_id)
        REFERENCES public.type_accounts(id)
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.students (
    student_id BIGSERIAL PRIMARY KEY,
    promotion_id BIGINT,
    etablissement_id BIGINT,
    matricule VARCHAR(100),
    user_id BIGINT,
    first_name VARCHAR(150) NOT NULL,
    last_name VARCHAR(150) NOT NULL,
    full_name VARCHAR(300) NOT NULL,
    gender CHAR(1),

    CONSTRAINT fk_student_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.teachers (
    teacher_id BIGSERIAL PRIMARY KEY,
    matricule VARCHAR(100),
    type_teacher_id BIGINT,
    departement VARCHAR(150),
    justificatif TEXT NOT NULL,
    faculte_id BIGINT NOT NULL,
    user_id BIGINT,
    first_name VARCHAR(150) NOT NULL,
    last_name VARCHAR(150) NOT NULL,
    full_name VARCHAR(300) NOT NULL,
    gender CHAR(1),

    CONSTRAINT fk_teacher_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_teacher_type
        FOREIGN KEY (type_teacher_id)
        REFERENCES public.teacher_types(id)
        ON DELETE RESTRICT
);