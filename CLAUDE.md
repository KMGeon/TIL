# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

This is a personal TIL (Today I Learn) repository organized as a knowledge management system for technical learning and documentation. The repository contains study notes, documentation, and learning resources across multiple technology domains.

## Repository Structure

### Core Organization
- `TIL/` - Daily learning entries organized by year and month (2024/, 2025/)
- `basic/` - Fundamental computer science concepts and data structures
- `java/` - Java programming language documentation and advanced concepts
- `Spring/` - Spring framework documentation, particularly Spring Batch
- `kafka/` - Apache Kafka concepts and programming guides
- `book/` - Technical book summaries and notes (SQL 레벨업, SQL첫걸음)
- `front/` - Frontend development resources (React, CSS)
- `img/` - Centralized image storage for documentation

### Documentation Language
The repository is primarily written in Korean, with technical content covering:
- Backend technologies (Java, Spring, Kafka)
- Database technologies (SQL, MySQL)
- Frontend technologies (React, TypeScript)
- Computer science fundamentals

## Development Tools and Scripts

### Image Management
- `transform_img_name.sh` - Utility script for managing images in documentation
  - Usage: `./transform_img_name.sh <source_directory>`
  - Moves images to centralized `img/` directory with UUID-based naming

### IDE Configuration
- `.cursor/rules/test.md` - Contains React/MUI/TypeScript development preferences and guidelines
- `.idea/` - IntelliJ IDEA project configuration

## Working with This Repository

### Adding New Content
- TIL entries should be placed in `TIL/YYYY/MM/` following the date format (e.g., `0821.md`)
- Technical documentation should be categorized into appropriate subject directories
- Images should be processed through the `transform_img_name.sh` script for consistency

### File Organization Patterns
- Markdown files use Korean titles and content
- Technical concepts are organized by subject area
- Daily learning entries include practical examples and problem-solving approaches

## Special Considerations

This repository serves as a personal knowledge base rather than a collaborative project. Content is primarily in Korean and focuses on backend development technologies with some frontend coverage. The structure prioritizes learning progression and easy reference over traditional software development workflows.