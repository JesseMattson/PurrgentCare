# 📫 Postman

---  

## Description

Postman is an API platform for building and using APIs. Postman simplifies each step of the API lifecycle and
streamlines collaboration so you can create better APIs—faster.
[Documentation][postman-link].

---

## 🔄 Workflow

1. [Import](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data/)
   "postman_collection.json" from repository.
2. Create new
   Postman [request](https://learning.postman.com/docs/getting-started/first-steps/sending-the-first-request/)
   in the apps [Collections](https://learning.postman.com/docs/collections/using-collections/)
3. Add [variables](https://learning.postman.com/docs/sending-requests/variables/variables/) for requests as needed.
4. Save new postman request.
5. Add any needed "seed" data to src/main/resources/import.sql
6. Run local copy of app by clicking play button. [Shift + F10] (Windows Only). Make sure you're not running an
   individual test etc.
7. Send Postman request(s). [Ctrl + <-] (Windows Only)
8. [Export](https://learning.postman.com/docs/getting-started/importing-and-exporting/exporting-data/) data from
   Postman.
9. Save over current "postman_collection.json" with latest collection export.

## 🏃 Running the collection

### Options:

- Postman using the [Workflow above](#-workflow)
- [Newman][newman-link] using the makefile command

```shell
make test-postman
```

___

### [Back to Documentation](../README.md)

[postman-link]: https://www.postman.com/product/what-is-postman/

[newman-link]: https://learning.postman.com/docs/collections/using-newman-cli/installing-running-newman/