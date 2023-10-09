package ru.yandex.practicum.filmorate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.event.EventOperations;
import ru.yandex.practicum.filmorate.model.event.EventTypes;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventReviewDto {

    LocalDateTime timestamp;
    EventTypes eventType;
    EventOperations operation;
    String content;
}
