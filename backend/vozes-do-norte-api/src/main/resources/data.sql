-- Inserir cidades iniciais
INSERT INTO cities (id, name) VALUES
    (gen_random_uuid(), 'Marabá'),
    (gen_random_uuid(), 'Santarém'),
    (gen_random_uuid(), 'Breves'),
    (gen_random_uuid(), 'Marituba'),
    (gen_random_uuid(), 'Belém')
ON CONFLICT DO NOTHING;