SET search_path TO public;

CREATE TABLE IF NOT EXISTS public.evaluations (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    file_content TEXT,
    user_id BIGINT NOT NULL,
    compteur BIGINT DEFAULT 0,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_evaluation_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.evaluation_questions (
    id BIGSERIAL PRIMARY KEY,
    evaluation_id BIGINT NOT NULL,
    title TEXT NOT NULL,
    point DOUBLE PRECISION DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_evaluation_question_evaluation
        FOREIGN KEY (evaluation_id)
        REFERENCES public.evaluations(id)
        ON DELETE CASCADE
);
