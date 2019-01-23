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
			   tipoUsuarioId int,
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
											p.tipo_usuario_id tipoUsuarioId,
										  	p.nombre tipoUsuario 
										  from (
											  select m.security_user_id id,
											  		 m.fecha_creacion fechaCreacion,
											      	 concat(m.apellido_paterno,' ',m.apellido_materno,' ', m.nombres) nombreCompleto,
											  		 m.flag_activo flagActivo,
											  		 m.correo,
											   		 m.username,
											   		 m.tipo_usuario_id,
											         m.fecha_ultimo_acceso fechaUltimoAcceso
											  from administrador m
											  WHERE ($1 IS NULL OR lower(concat(m.apellido_paterno,' ',m.apellido_materno,' ', m.nombres)) like lower(CONCAT('%',$1,'%')))
											  AND ($2 IS NULL OR m.flag_activo = $2)
											  union all
											  select t.security_user_id id,
											  		 t.fecha_creacion fechaCreacion,
											      	 concat(t.apellido_paterno,' ',t.apellido_materno,' ', t.nombres) nombreCompleto,
											  		 t.flag_activo flagActivo,
											  		 t.correo,
											   		 t.username,
											   		 t.tipo_usuario_id,
											         t.fecha_ultimo_acceso fechaUltimoAcceso
											  from trainer t
											  WHERE ($1 IS NULL OR lower(concat(t.apellido_paterno,' ',t.apellido_materno,' ', t.nombres)) like lower(CONCAT('%',$1,'%')))
											  AND ($2 IS NULL OR t.flag_activo = $2)
											  union all
											  select u.security_user_id id,
											  		 u.fecha_creacion fechaCreacion,
											         concat(u.apellido_paterno,' ',u.apellido_materno,' ', u.nombres) nombreCompleto,
											  		 u.flag_activo flagActivo,
											  		 u.correo,
											  		 u.username,
											  		 u.tipo_usuario_id,
											    	 u.fecha_ultimo_acceso fechaUltimoAcceso
											  from cliente u
											  WHERE ($1 IS NULL OR lower(concat(u.apellido_paterno,' ',u.apellido_materno,' ', u.nombres)) like lower(CONCAT('%',$1,'%')))
											  AND ($2 IS NULL OR u.flag_activo = $2)
											  order by 1) a
										  	inner join tipo_usuario p on a.tipo_usuario_id=p.tipo_usuario_id
$func$ LANGUAGE sql;