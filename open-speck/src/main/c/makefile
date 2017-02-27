SHELL 	= /bin/sh
CC    	= gcc
SOURCES = $(shell echo *.c)
HEADERS = $(shell echo include/*.h)
ODIR	= obj
OBJECTS = $(patsubst %,$(ODIR)/%,$(SOURCES:.c=.o))
RM 		= rm -rf

ifndef JAVA_HOME
	TEST := $(shell which java)
	ifdef TEST
		JAVA_HOME := $(shell readlink -f $(TEST) | xargs dirname | xargs dirname )
	endif
endif

ifeq ($(OS),Windows_NT)
    CFLAGS =
	FLAGS =
	TARGET = speck.dll
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S),Linux)
        CFLAGS = -std=gnu99 -Iinclude -fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
		FLAGS = -fPIC -g #-pedantic -Wall -Wextra -ggdb3
		#FLAGS =  -g #-pedantic -Wall -Wextra -ggdb3
		LDFLAGS = -shared
		TARGET = libspeck.so
    endif
    ifeq ($(UNAME_S),Darwin)
        CFLAGS = -I/System/Library/Frameworks/JavaVM.framework/Headers
		FLAGS = -dynamiclib
		LDFLAGS = -framework JavaVM
		TARGET = libspeck.dylib
    endif
endif

all: $(TARGET)

$(TARGET): $(OBJECTS)
	$(CC) $(FLAGS) -o $@ $^ $(LDFLAGS)

$(ODIR)/%.o: %.c $(HEADERS)
	@mkdir -p $(ODIR)
	$(CC) $(CFLAGS) -c $< -o $@

.PHONY: clean

clean:
	-$(RM) $(TARGET) $(ODIR)
