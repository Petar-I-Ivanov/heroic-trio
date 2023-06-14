package com.github.heroictrio.encoder;

public interface IdEncoder {

  String encode(Long id);

  Long decode(String encodedId);
}
