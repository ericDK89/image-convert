# image-convert

A command-line tool built with Java and Spring Shell to convert images from formats like PNG, JPEG, BMP, and GIF into the modern, efficient WebP format.

## Features

*   **Single File Conversion:** Convert a single image file to WebP.
*   **Batch Directory Conversion:** Convert all supported images within a specified directory.
*   **Adjustable Quality:** Control the compression quality of the output WebP images.
*   **Progress Bar:** Visual feedback on the progress of batch conversions.
*   **Multiple Input Formats:** Supports `.jpg`, `.jpeg`, `.png`, `.bmp`, `.gif`, and `.tiff`.

## Prerequisites

*   Java 17 or later
*   Maven (the project includes a Maven wrapper `mvnw`)

## Installation and Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/ericdk89/image-convert.git
    cd image-convert
    ```

2.  **Build the project:**
    Use the included Maven wrapper to build the project and create an executable JAR file.

    On Linux/macOS:
    ```sh
    ./mvnw clean package
    ```

    On Windows:
    ```sh
    .\mvnw.cmd clean package
    ```
    This will generate the JAR file in the `target/` directory (e.g., `target/image-convert-0.0.1-SNAPSHOT.jar`).

## Usage

Run the application from the command line using the generated JAR file. This will launch the Spring Shell interactive prompt.

```sh
java -jar target/image-convert-*.jar
```

Once the shell is active, you can use the `webp` command to perform conversions.

The converted images are saved in the `src/static/images/` directory within the project folder.

### Command Syntax

The primary command is `webp` with the following options:

```
webp --s <file-path> --q <quality>
webp --d <directory-path> --q <quality>
```

**Options:**

*   `--s <file-path>`: (Single) The path to a single image file you want to convert.
*   `--d <directory-path>`: (Directory) The path to a directory containing images you want to convert.
*   `--q <quality>`: (Quality) A floating-point number between `0.0` (lowest quality) and `1.0` (highest quality). The default is `0.75`.

**Note:** You must use either `--s` or `--d`, but not both at the same time.

### Examples

#### Convert a Single Image

To convert a single file named `photo.png` with the default quality (0.75):

```shell
shell:> webp --s /path/to/your/photo.png
```

#### Convert a Single Image with Custom Quality

To convert `photo.png` with a quality setting of 0.5:

```shell
shell:> webp --s /path/to/your/photo.png --q 0.5
```

#### Convert All Images in a Directory

To convert all supported images in a directory named `my-pictures` with the default quality:

```shell
shell:> webp --d /path/to/your/my-pictures
```
A progress bar will be displayed during the conversion process.

```
Converting Images |████████████████████████████████████████| 10/10 (100%)
```

## Core Dependencies

*   **Spring Shell:** Provides the framework for the interactive command-line interface.
*   **WebP ImageIO Sejda:** A library that adds WebP read/write capabilities to Java's ImageIO.
*   **ProgressBar:** Used to create the command-line progress bar for batch operations.
