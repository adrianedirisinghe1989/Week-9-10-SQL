DROP TABLE IF EXISTS  material;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS project;



CREATE TABLE project(
project_id INT /*AUTO_INCREMENT*/ NOT NULL,
project_name VARCHAR(128) NOT NULL,
notes TEXT,
num_servings INT,
estimated_hours TIME,
actual_hours TIME,
created_at TIMESTAMP NOT NULL Default CURRENT_TIMESTAMP,
PRIMARY KEY( project_id)
);

CREATE TABLE category(
category_id INT /*AUTO_INCREMENT*/ NOT NULL,
category_name VARCHAR(64) NOT NULL,
PRIMARY KEY(category_id)
);


CREATE TABLE project_category(
project_id INT NOT NULL,
category_id INT NOT NULL,
FOREIGN KEY(project_id) references project (project_id)on delete cascade,
FOREIGN KEY(project_id) references  category(category_id) on delete cascade,
UNIQUE KEY (project_id,category_id)
);

CREATE TABLE step(
step_id INT /*AUTO_INCREMENT*/ NOT NULL,
project_id INT NOT NULL,
step_order INT NOT NULL,
step_text TEXT NOT NULL,
PRIMARY KEY (step_id),
FOREIGN KEY(project_id) references project (project_id) on delete cascade
);


CREATE TABLE material(
material_id INT /*AUTO_INCREMENT*/ NOT NULL,
project_id INT NOT NULL,
material_name VARCHAR(64) NOT NULL,
num_required INT NOT NULL,
cost BIGDECIMAL(7,2),
PRIMARY KEY (material_id),
FOREIGN KEY (project_id) references project (project_id)on delete cascade
);


/*CREATE TABLE unit(
unit_id INT AUTO_INCREMENT NOT NULL,
unit_name_singular VARCHAR(32) NOT NULL,
unit_name_plural VARCHAR(34) NOT NULL,
primary key(unit_id)
);
*/


/*foreign KEY(unit_id) references unit (unit_id)*/

