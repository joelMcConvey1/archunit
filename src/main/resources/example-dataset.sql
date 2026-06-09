-- Pre-populate jobs table for dev profile
INSERT INTO jobs (JOB_NAME, JOB_DESCRIPTION, CAPABILITY, BAND, CREATED_AT)
VALUES
  ('AI Apprentice', 'Support data and AI initiatives and learn foundational skills.', 'DATA_AND_AI', 'APPRENTICE', CURRENT_TIMESTAMP),
  ('AI Trainee', 'Assist with data preparation and basic AI model training.', 'DATA_AND_AI', 'TRAINEE', CURRENT_TIMESTAMP),
  ('Data Associate', 'Work on data analysis and reporting tasks.', 'DATA_AND_AI', 'ASSOCIATE', CURRENT_TIMESTAMP),
  ('Senior Data Scientist', 'Lead advanced analytics and AI projects.', 'DATA_AND_AI', 'SENIOR_ASSOCIATE', CURRENT_TIMESTAMP),
  ('Data Consultant', 'Advise clients on data strategy and AI adoption.', 'DATA_AND_AI', 'CONSULTANT', CURRENT_TIMESTAMP),
  ('Principal Data Architect', 'Design enterprise data and AI architectures.', 'DATA_AND_AI', 'PRINCIPAL', CURRENT_TIMESTAMP),

  ('Delivery Apprentice', 'Learn delivery processes and support project teams.', 'DELIVERY', 'APPRENTICE', CURRENT_TIMESTAMP),
  ('Delivery Trainee', 'Assist in project delivery and documentation.', 'DELIVERY', 'TRAINEE', CURRENT_TIMESTAMP),
  ('Delivery Associate', 'Coordinate project tasks and client communications.', 'DELIVERY', 'ASSOCIATE', CURRENT_TIMESTAMP),
  ('Senior Delivery Manager', 'Oversee multiple delivery teams and ensure quality.', 'DELIVERY', 'SENIOR_ASSOCIATE', CURRENT_TIMESTAMP),
  ('Delivery Consultant', 'Provide delivery best practices and process improvements.', 'DELIVERY', 'CONSULTANT', CURRENT_TIMESTAMP),
  ('Principal Delivery Lead', 'Lead strategic delivery initiatives across programs.', 'DELIVERY', 'PRINCIPAL', CURRENT_TIMESTAMP),

  ('Software Engineering Apprentice', 'Learn software engineering basics and support teams.', 'ENGINEERING', 'APPRENTICE', CURRENT_TIMESTAMP),
  ('Engineering Trainee', 'Support engineering teams and learn best practices.', 'ENGINEERING', 'TRAINEE', CURRENT_TIMESTAMP),
  ('Software Engineer', 'Design and build software systems.', 'ENGINEERING', 'ASSOCIATE', CURRENT_TIMESTAMP),
  ('Senior Software Engineer', 'Lead engineering teams and mentor associates.', 'ENGINEERING', 'SENIOR_ASSOCIATE', CURRENT_TIMESTAMP),
  ('Engineering Consultant', 'Provide expert engineering advice to clients.', 'ENGINEERING', 'CONSULTANT', CURRENT_TIMESTAMP),
  ('Principal Engineer', 'Drive technical excellence and innovation.', 'ENGINEERING', 'PRINCIPAL', CURRENT_TIMESTAMP),

  ('HR Apprentice', 'Support HR operations and learn HR processes.', 'HUMAN_RESOURCES', 'APPRENTICE', CURRENT_TIMESTAMP),
  ('HR Trainee', 'Assist with recruitment and onboarding.', 'HUMAN_RESOURCES', 'TRAINEE', CURRENT_TIMESTAMP),
  ('HR Associate', 'Coordinate HR activities and employee relations.', 'HUMAN_RESOURCES', 'ASSOCIATE', CURRENT_TIMESTAMP),
  ('Senior HR Specialist', 'Lead HR projects and mentor junior staff.', 'HUMAN_RESOURCES', 'SENIOR_ASSOCIATE', CURRENT_TIMESTAMP),
  ('HR Consultant', 'Advise on HR strategy and compliance.', 'HUMAN_RESOURCES', 'CONSULTANT', CURRENT_TIMESTAMP),
  ('Principal HR Partner', 'Shape organizational HR strategy.', 'HUMAN_RESOURCES', 'PRINCIPAL', CURRENT_TIMESTAMP),

  ('QA Apprentice', 'Learn QA methodologies and assist with testing.', 'QUALITY_ASSURANCE', 'APPRENTICE', CURRENT_TIMESTAMP),
  ('QA Trainee', 'Support QA teams and execute test cases.', 'QUALITY_ASSURANCE', 'TRAINEE', CURRENT_TIMESTAMP),
  ('QA Associate', 'Test software and report bugs.', 'QUALITY_ASSURANCE', 'ASSOCIATE', CURRENT_TIMESTAMP),
  ('Senior QA Consultant', 'Lead QA teams and ensure quality standards.', 'QUALITY_ASSURANCE', 'SENIOR_ASSOCIATE', CURRENT_TIMESTAMP),
  ('QA Consultant', 'Advise on QA best practices and process improvements.', 'QUALITY_ASSURANCE', 'CONSULTANT', CURRENT_TIMESTAMP),
  ('Principal QA Lead', 'Drive QA strategy and automation.', 'QUALITY_ASSURANCE', 'PRINCIPAL', CURRENT_TIMESTAMP),

  ('Product Apprentice', 'Support product teams and learn product lifecycle.', 'PRODUCT', 'APPRENTICE', CURRENT_TIMESTAMP),
  ('Product Trainee', 'Assist in product development and research.', 'PRODUCT', 'TRAINEE', CURRENT_TIMESTAMP),
  ('Product Associate', 'Assist in product development and market research.', 'PRODUCT', 'ASSOCIATE', CURRENT_TIMESTAMP),
  ('Senior Product Consultant', 'Lead product strategy and client workshops.', 'PRODUCT', 'SENIOR_ASSOCIATE', CURRENT_TIMESTAMP),
  ('Product Consultant', 'Guide clients on product strategy.', 'PRODUCT', 'CONSULTANT', CURRENT_TIMESTAMP),
  ('Principal Product Manager', 'Lead product vision and roadmap.', 'PRODUCT', 'PRINCIPAL', CURRENT_TIMESTAMP);
