# Git flow

___

### Description

[Git flow](https://docs.github.com/en/get-started/using-github/github-flow) is a branch-based workflow that is leverage 
by [GitHub](https://github.com/), [GitLab](https://about.gitlab.com/) and other similar version control systems.

___

## ⚙️ Common git commands

| Command                             | Definition                                                                                                    |
|-------------------------------------|---------------------------------------------------------------------------------------------------------------|
| **git pull**                        | Updates your current local working branch with all new commits from the corresponding remote branch on GitHub |
| **git checkout** _{branch-name}_    | Switches to the specified branch and updates the working directory                                            |
| **git branch** _{branch-name}_      | Creates a new branch                                                                                          |
| **git status**                      | Show all files additions/deletions/modifications of the current local working branch                          |
| **git add** _{document}_            | Adds the file in preparation for committing to version control                                                |
| **git commit -m** _"{message}"_     | Records files permanently in version history with message                                                     |
| **git push origin** _{branch-name}_ | Uploads all local branch commits to GitHub                                                                    |
| **git merge** _{branch-name}_       | Combines remote tracking branch into current local branch                                                     |

** Text in **bold** is standard command and text in _italics_ are variables.\
** Also see [Git cheat sheet](https://training.github.com/downloads/github-git-cheat-sheet.pdf)

___

## Branching naming convention

| Base branch name      | Types of changes                        |
|-----------------------|-----------------------------------------|
| **feature/**_{name}_  | new functionality or feature            |
| **chore/**_{name}_    | documentation or administrative changes |
| **bugfix/**_{name}_   | patching a bug or issue found           |       
| **pipeline/**_{name}_ | new pipeline changes                    |

___

## Sample git flow on this project

**Sample story**: Implement new feature for etc...

#### Logical steps with terminal commands:
1. Get current base branch
   1. ```git checkout main```
   2. ```git pull```
2. Create branch from base branch
   1. ```git branch feature/{feature_name}```
3. Perform code changes to meet Definition of Done
   1. See future documentation for DoD (code changes, tests, etc.)
4. Verify files to commit changes
   1. ```git status```
   2. Review output for changes
5. Add changes to be committed in each commit
   1. ```git add {filename}``` for each file
6. Commit changes that were added with a descriptive message
   1. ```git commit -m {descriptive_message}```
7. Push changes to remote branch in Github
   1. ```git push origin feature/{feature_name}```

** All commands above are terminal based commands. For GUI usage in IntelliJ see [documentation](https://www.jetbrains.com/help/idea/using-git-integration.html)

___

[Back to Documentation](../README.md)