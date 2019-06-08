CREATE OR REPLACE FUNCTION func_users_q_dynamic_where(
     _ad_nombre_full       text  = NULL,
	 _ad_flag_activo      boolean = NULL)
  RETURNS TABLE(id int, 
				nombreCompleto text,
			   fechaCreacion timestamp,
			   username text,
			   correo text,
			   fechaUltimoAcceso timestamp,
			   flagActivo boolean,
			   tipoUsuario text) AS
$func$
SELECT * FROM tipo_usuario;
                      select
										  	a.id, 
										    a.nombreCompleto, 
 										    a.fechaCreacion, 
										    a.username, 
										    a.correo, 
										    a.fechaUltimoAcceso, 
										    a.flagActivo,
										  	tipoUsuario
										  from (
											  select m.security_user_id id,
											  		 m.fecha_creacion fechaCreacion,
											      	 concat(m.apellidos,' ', m.nombres) nombreCompleto,
											  		 m.flag_activo flagActivo,
											  		 m.correo,
											   		 m.username,
											         m.fecha_ultimo_acceso fechaUltimoAcceso,
											         'Administrador' tipoUsuario
											  from administrador m
											  WHERE ($1 IS NULL OR lower(concat(m.apellidos,' ', m.nombres)) like lower(CONCAT('%',$1,'%')))
											  AND ($2 IS NULL OR m.flag_activo = $2)
											  union all
											  select t.security_user_id id,
											  		 t.fecha_creacion fechaCreacion,
											      	 concat(t.apellidos,' ', t.nombres) nombreCompleto,
											  		 t.flag_activo flagActivo,
											  		 t.correo,
											   		 t.username,
											         t.fecha_ultimo_acceso fechaUltimoAcceso,
                                                     'Trainer' tipoUsuario
                                              from trainer t
											  WHERE ($1 IS NULL OR lower(concat(t.apellidos,' ', t.nombres)) like lower(CONCAT('%',$1,'%')))
											  AND ($2 IS NULL OR t.flag_activo = $2)
											  union all
											  select u.security_user_id id,
											  		 u.fecha_creacion fechaCreacion,
											         concat(u.apellidos,' ', u.nombres) nombreCompleto,
											  		 u.flag_activo flagActivo,
											  		 u.correo,
											  		 u.username,
											    	 u.fecha_ultimo_acceso fechaUltimoAcceso,
                                                     'Cliente' tipoUsuario
											  from cliente u
											  WHERE ($1 IS NULL OR lower(concat(u.apellidos,' ', u.nombres)) like lower(CONCAT('%',$1,'%')))
											  AND ($2 IS NULL OR u.flag_activo = $2)
											  order by 1) a
$func$ LANGUAGE sql;
