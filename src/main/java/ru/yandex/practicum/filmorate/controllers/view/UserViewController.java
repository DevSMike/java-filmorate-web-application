package ru.yandex.practicum.filmorate.controllers.view;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.event.Event;
import ru.yandex.practicum.filmorate.service.EventService;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserViewController {

    private final UserService userService;
    private final EventService eventService;
    private final FilmService filmService;


    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(User user) {
        log.info("user: {}", user);
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String getUsers(Model model) {
        log.info("Getting users");
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam long userId) {
        log.info("Delete request to remove user with id {}", userId);
        userService.deleteUser(userId);
        return "redirect:/users";
    }

    @GetMapping("/rates")
    public String addRateForm(Model model) {
        Collection<Film> films = filmService.getAllFilms();
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("films", films);
        model.addAttribute("users", users);
        return "film-rates";
    }

    @PostMapping("/rates/add")
    public String addRate(Long filmId, Long userId, Integer rate) {
        log.info("Put request to add rate {} to film: {} from user: {}", rate, filmId, userId);
        filmService.addRate(userId, filmId, rate);
        return "redirect:/users";
    }

    @PostMapping("/rates/delete")
    public String deleteRate(Long filmId, Long userId) {
        log.info("Delete request to remove rate from film: {} from user: {}", filmId, userId);
        filmService.removeRate(userId, filmId);
        return "redirect:/users";
    }

    @GetMapping("/friends")
    public String addFriendsForm(Model model) {
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "friends";
    }

    @PostMapping("friends/add")
    public String addFriend(long userId, long friendId) {
        log.info("Put request to add friend id: {} friendId: {}", userId, friendId);
        userService.addFriend(userId, friendId);
        return "redirect:/users";
    }

    @PostMapping("friends/delete")
    public String deleteFriend(long userId, long friendId) {
        log.info("Put request to delete friend id: {} friendId: {}", userId, friendId);
        userService.removeFriend(userId, friendId);
        return "redirect:/users";
    }

    @GetMapping("/friends/user")
    public String getFriendsListForm(Model model) {
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-friends";
    }

    @PostMapping("/friends/user")
    public String getFriendsList(@RequestParam("selectedUserId") Long id, Model model) {
        log.info("Get request for all friends list for user: {}", id);
        Collection<User> friends = userService.getFriendsList(id);
        model.addAttribute("friends", friends);
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-friends";
    }

    @GetMapping("/friends/user/common")
    public String getCommonFriendsList(Model model) {
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-common-friends";
    }

    @PostMapping("/friends/user/common")
    public String getCommonFriendsList(@RequestParam("id") Long id, @RequestParam("otherId") Long otherId, Model model) {
        log.info("Get request for common friends userId: {} friendId: {}", id, otherId);
        Collection<User> commonFriends = userService.getCommonFriendsList(id, otherId);
        model.addAttribute("commonFriends", commonFriends);
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-common-friends";
    }

    @GetMapping("/feed")
    public String getUserEventsForm(Model model) {
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-feed";
    }

    @PostMapping("/feed")
    public String getUserEvents(@RequestParam("id") Long id, Model model) {
        log.info("Get request for userId {} events", id);
        Collection<Event> events = eventService.getUserEvents(id);
        model.addAttribute("events", events);
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-feed";
    }

    @GetMapping("/{id}/recommendations")
    public Collection<Film> getRecommendations(@PathVariable long id) {
        log.info("Get request for recommendation for user with id: {} ", id);
        return userService.getRecommendation(id);
    }

    @GetMapping("/films/common")
    public String getCommonFilmsForm(Model model) {
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "films-common";
    }
    @PostMapping("/films/common")
    public String getCommonFilms(@RequestParam Long userId, @RequestParam Long friendId, Model model) {
        log.info("Get common films for users: {} {}", userId, friendId);
        Collection<Film> commonFilms = filmService.getCommonFilms(userId, friendId);
        model.addAttribute("commonFilms", commonFilms);
        Collection<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "films-common";
    }

}
