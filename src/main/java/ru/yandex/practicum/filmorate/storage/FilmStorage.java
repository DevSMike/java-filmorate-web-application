package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);

    Film updateFilm(Film film);

    Collection<Film> getAllFilms();

    Collection<Film> getTopFilms(int size);

    boolean deleteFilm(Film film);

    Film getById(long id);

    Collection<Film> getFilmsForDirectorSorted(Long id, String sortBy);

    Collection<Film> getCommonFilms(long uid, long fid);
}