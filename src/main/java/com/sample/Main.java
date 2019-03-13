package com.sample;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
  Injector injector = Guice.createInjector(new BasicModule());
  ContactBookService contactBookService =injector.getInstance(ContactBookService.class);


}
