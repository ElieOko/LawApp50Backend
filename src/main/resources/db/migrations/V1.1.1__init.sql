SET search_path TO public;

CREATE TABLE IF NOT EXISTS public.categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    is_active BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.scopes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    is_active BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.type_contenus (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    is_active BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.contenus (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    file_content TEXT NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_contenu_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.categories_contenus (
    id BIGSERIAL PRIMARY KEY,
    categorie_id BIGINT NOT NULL,
    contenu_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_categorie_contenu_categorie
        FOREIGN KEY (categorie_id)
        REFERENCES public.categories(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_categorie_contenu_contenu
        FOREIGN KEY (contenu_id)
        REFERENCES public.contenus(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.scope_contenus (
    id BIGSERIAL PRIMARY KEY,
    scope_id BIGINT NOT NULL,
    contenu_id BIGINT NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_scope_contenu_scope
        FOREIGN KEY (scope_id)
        REFERENCES public.scopes(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_scope_contenu_contenu
        FOREIGN KEY (contenu_id)
        REFERENCES public.contenus(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.commentaire_contenus (
    id BIGSERIAL PRIMARY KEY,
    contenu_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    description TEXT NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_commentaire_contenu
        FOREIGN KEY (contenu_id)
        REFERENCES public.contenus(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_commentaire_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.commentaire_response_contenus (
    id BIGSERIAL PRIMARY KEY,
    commentaire_contenu_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    description TEXT NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_response_commentaire
        FOREIGN KEY (commentaire_contenu_id)
        REFERENCES public.commentaire_contenus(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_response_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.like_contenus (
    id BIGSERIAL PRIMARY KEY,
    contenu_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    is_like BOOLEAN DEFAULT TRUE,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_like_contenu
        FOREIGN KEY (contenu_id)
        REFERENCES public.contenus(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_like_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_like_user_contenu
        UNIQUE (contenu_id, user_id)
);

CREATE TABLE IF NOT EXISTS public.favoris_contenus (
    id BIGSERIAL PRIMARY KEY,
    contenu_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    favorite BOOLEAN DEFAULT TRUE,
    is_active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_favoris_contenu
        FOREIGN KEY (contenu_id)
        REFERENCES public.contenus(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_favoris_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_favoris_user_contenu
        UNIQUE (contenu_id, user_id)
);

CREATE TABLE IF NOT EXISTS public.avis_contenus (
    id BIGSERIAL PRIMARY KEY,
    contenu_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    description TEXT,
    cote BIGINT DEFAULT 0,
    is_active BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_avis_contenu
        FOREIGN KEY (contenu_id)
        REFERENCES public.contenus(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_avis_user
        FOREIGN KEY (user_id)
        REFERENCES public.users(id)
        ON DELETE CASCADE
);