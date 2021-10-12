package com.gralak.moviestore.movie;

import com.gralak.moviestore.exception.EmptyTitleException;
import com.gralak.moviestore.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService
{
    private final MovieRepo movieRepo;

    public List<Movie> getMovies()
    {
        return movieRepo.findAll();
    }

    public Movie getMovieById(Long id)
    {
        return movieRepo.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    public Movie addMovie(Movie movieToSave)
    {
        if (movieToSave.getTitle() == null || movieToSave.getTitle().length() == 0)
        {
            throw new EmptyTitleException();
        }
        return movieRepo.save(movieToSave);
    }

    public Movie updateMovie(Movie movieForUpdate)
    {
        boolean exists = movieRepo.existsById(movieForUpdate.getId());
        if (!exists)
        {
            throw new MovieNotFoundException(movieForUpdate.getId());
        }
        if (movieForUpdate.getTitle() == null || movieForUpdate.getTitle().length() == 0)
        {
            throw new EmptyTitleException();
        }
        return movieRepo.save(movieForUpdate);
    }

    public void deleteMovieById(Long id)
    {
        boolean exists = movieRepo.existsById(id);
        if (!exists)
        {
            throw new MovieNotFoundException(id);
        }
        movieRepo.deleteById(id);
    }
}
