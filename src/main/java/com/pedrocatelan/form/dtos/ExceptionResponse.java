package com.pedrocatelan.form.dtos;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {
}
