SET search_path TO public;

CREATE TABLE IF NOT EXISTS public.account_users (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_account_user_account
        FOREIGN KEY (account_id)
        REFERENCES public.accounts(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_account_user_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_account_user
        UNIQUE (account_id, user_id)
);

CREATE TABLE IF NOT EXISTS public.teacher_etablissements (
    id BIGSERIAL PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    etablissement_id BIGINT NOT NULL,

    CONSTRAINT fk_teacher_etablissement_teacher
        FOREIGN KEY (teacher_id)
        REFERENCES public.teachers(teacher_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_teacher_etablissement_etablissement
        FOREIGN KEY (etablissement_id)
        REFERENCES public.etablissements(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_teacher_etablissement
        UNIQUE (teacher_id, etablissement_id)
);