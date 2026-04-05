SET search_path TO public;

CREATE TABLE IF NOT EXISTS public.quizzes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL DEFAULT '',
    user_id BIGINT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_quizzes_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.quiz_levels (
    id BIGSERIAL PRIMARY KEY,
    quiz_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    level_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_quiz_levels_quiz
        FOREIGN KEY (quiz_id)
        REFERENCES public.quizzes(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.quiz_questions (
    id BIGSERIAL PRIMARY KEY,
    quiz_level_id BIGINT NOT NULL,
    title TEXT NOT NULL,
    point DOUBLE PRECISION NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_quiz_questions_level
        FOREIGN KEY (quiz_level_id)
        REFERENCES public.quiz_levels(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.quiz_question_options (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL,
    option_text TEXT NOT NULL,
    is_valid BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_quiz_question_options_question
        FOREIGN KEY (question_id)
        REFERENCES public.quiz_questions(id)
        ON DELETE CASCADE
);
