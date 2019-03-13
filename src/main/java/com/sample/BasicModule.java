package com.sample;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Database.class).to(Database.class);
    bind(ContactBookService.class).toInstance(new ContactBookService(new Database()));
  }
}