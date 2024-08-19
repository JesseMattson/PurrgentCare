# 🤫 GitHub Secrets

___

### Description

[GitHub secrets][github-secrets-link] allow you to store sensitive information in your
organization, repository, or repository environments.

___

### Adding a Secret to GitHub Repository

To add a secret to a GitHub Repository follow these steps:

1. On GitHub.com, navigate to the main page of the repository.
2. Under your repository name, click Settings. If you cannot see the "Settings" tab,
   select the dropdown menu, then click Settings.
3. In the "Security" section of the sidebar, select Secrets and variables,
   then click Actions.
4. Click the Secrets tab.
5. Click New repository secret.
6. In the Name field, type a name for your secret.
7. In the Secret field, enter the value for your secret.
8. Click Add secret.

---

### Using Secrets in a Workflow

See [GitHub Documentation][github-using-secrets-workflow-link]

---


[github-secrets-link]: https://docs.github.com/en/actions/security-for-github-actions/security-guides/using-secrets-in-github-actions

[github-using-secrets-workflow-link]:https://docs.github.com/en/actions/security-for-github-actions/security-guides/using-secrets-in-github-actions#using-secrets-in-a-workflow