package com.example.restfullapi.model;

public class modelMahasiswa {
    int id;
    String nama;
    String jurusan;
    String semester;

    public modelMahasiswa(int id, String nama, String jurusan, String semester) {
        this.id = id;
        this.nama = nama;
        this.jurusan = jurusan;
        this.semester = semester;
    }

    public modelMahasiswa(String nama, String jurusan, String semester) {
        this.nama = nama;
        this.jurusan = jurusan;
        this.semester = semester;
    }

    public int getId() {
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
