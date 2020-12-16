package com.example.restfullapi.model;

public class modelMahasiswa {
    String id;
    String nama;
    String jurusan;
    String semester;

    public modelMahasiswa(String id, String nama, String jurusan, String semester) {
        this.id = id;
        this.nama = nama;
        this.jurusan = jurusan;
        this.semester = semester;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getSemester() {
        return semester;
    }
}
