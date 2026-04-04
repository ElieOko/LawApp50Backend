SET search_path TO public;

CREATE TABLE IF NOT EXISTS public.status_posts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    background_color VARCHAR(64) NOT NULL DEFAULT '',
    note TEXT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_status_posts_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);
