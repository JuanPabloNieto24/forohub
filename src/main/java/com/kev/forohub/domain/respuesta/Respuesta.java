package com.kev.forohub.domain.respuesta;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kev.forohub.domain.shared.Status;
import com.kev.forohub.domain.topico.Topico;
import com.kev.forohub.infra.security.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    @JsonBackReference
    private Topico topico;
    private LocalDateTime fechaCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario autor;
    @Column(unique = true)
    private String solucion;
    private Status status;

    public Respuesta(DatosRegistroRespuesta datos,Topico topico,Usuario usuario) {
        this.mensaje = datos.mensaje();
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = usuario;
        this.solucion = datos.solucion();
        this.status = Status.CREATED;
    }

    public void updateDatos(DatosUpdateRespuesta datos) {
        if(datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }

        if(datos.solucion() != null){
            this.solucion = datos.solucion();
        }

        this.status = Status.UPDATED;
    }
}
