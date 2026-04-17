SET search_path TO public;

CREATE TABLE IF NOT EXISTS public.question_options (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL,
    option TEXT NOT NULL,
    is_valid BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_question_options_evaluation_question
        FOREIGN KEY (question_id)
        REFERENCES public.evaluation_questions(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.question_ouvertes (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL,
    title TEXT NOT NULL,
    file_content TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_question_ouverte_evaluation_question
        FOREIGN KEY (question_id)
        REFERENCES public.evaluation_questions(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.question_case_studys (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL,
    title TEXT NOT NULL,
    file_content TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_question_case_evaluation_question
        FOREIGN KEY (question_id)
        REFERENCES public.evaluation_questions(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.evaluation_submissions (
    id BIGSERIAL PRIMARY KEY,
    evaluation_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_evaluation_submission_user UNIQUE (evaluation_id, user_id),

    CONSTRAINT fk_eval_submission_evaluation
        FOREIGN KEY (evaluation_id)
        REFERENCES public.evaluations(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_eval_submission_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.evaluation_submission_answers (
    id BIGSERIAL PRIMARY KEY,
    submission_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    selected_option_id BIGINT,
    text_response TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_submission_question UNIQUE (submission_id, question_id),

    CONSTRAINT fk_eval_answer_submission
        FOREIGN KEY (submission_id)
        REFERENCES public.evaluation_submissions(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_eval_answer_question
        FOREIGN KEY (question_id)
        REFERENCES public.evaluation_questions(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_eval_answer_option
        FOREIGN KEY (selected_option_id)
        REFERENCES public.question_options(id)
        ON DELETE SET NULL
);
