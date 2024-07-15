package com.kev.forohub.domain.respuesta;

import com.kev.forohub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    boolean existsByMensaje(String mensaje);
    boolean existsBySolucion(String solucion);
    Page<Respuesta> findAllByTopico(Pageable pageable , Topico topico);
}
