package com.example.moway_rutas.Recorrido;

public class ModeloHorario {
    private String hora_inicio_semana;
    private String hora_final_semana;
    private String hora_inicio_festivo;
    private String hora_final_festivo;

    public ModeloHorario() {
    }

    public ModeloHorario(String hora_inicio_semana, String hora_final_semana, String hora_inicio_festivo, String hora_final_festivo) {
        this.hora_inicio_semana = hora_inicio_semana;
        this.hora_final_semana = hora_final_semana;
        this.hora_inicio_festivo = hora_inicio_festivo;
        this.hora_final_festivo = hora_final_festivo;
    }

    public String getHora_inicio_semana() {
        return hora_inicio_semana;
    }

    public void setHora_inicio_semana(String hora_inicio_semana) {
        this.hora_inicio_semana = hora_inicio_semana;
    }

    public String getHora_final_semana() {
        return hora_final_semana;
    }

    public void setHora_final_semana(String hora_final_semana) {
        this.hora_final_semana = hora_final_semana;
    }

    public String getHora_inicio_festivo() {
        return hora_inicio_festivo;
    }

    public void setHora_inicio_festivo(String hora_inicio_festivo) {
        this.hora_inicio_festivo = hora_inicio_festivo;
    }

    public String getHora_final_festivo() {
        return hora_final_festivo;
    }

    public void setHora_final_festivo(String hora_final_festivo) {
        this.hora_final_festivo = hora_final_festivo;
    }

    @Override
    public String toString() {
        return "ModeloHorario{" +
                "hora_inicio_semana='" + hora_inicio_semana + '\'' +
                ", hora_final_semana='" + hora_final_semana + '\'' +
                ", hora_inicio_festivo='" + hora_inicio_festivo + '\'' +
                ", hora_final_festivo='" + hora_final_festivo + '\'' +
                '}';
    }
}
