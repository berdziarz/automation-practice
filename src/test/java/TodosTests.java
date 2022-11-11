import io.restassured.response.Response;
import org.berdzik.rest.ResourcePath;
import org.berdzik.rest.Todo;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodosTests extends RestApiTests {

    @Test
    public void getAllTodos() {
        Response response = getAllResources(ResourcePath.TODOS.getPath());
        Todo[] todos = response.getBody().as(Todo[].class);

        assertThat(todos).hasSize(200);
        assertThat(todos).allSatisfy(e -> assertThat(e.getTitle()).as("Todo with id=" + e.getId() + " title assertion").isNotEmpty());
    }

    @Test
    public void getTodoById() {
        Todo expectedTodo = Todo.builder()
                .id(1)
                .userId(1)
                .title("delectus aut autem")
                .completed(false).build();

        Response response = getResourcesById(1, ResourcePath.TODOS.getPath());
        Todo todo = response.getBody().as(Todo.class);

        assertThat(expectedTodo)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(todo);
    }

    @Test
    public void getNonExistTodo() {
        checkGetNonExistResource(999, ResourcePath.TODOS.getPath());
    }

    @Test
    public void getTodosByUserId() {
        long userId = 10;

        Response response = getResourceByUserId(userId, ResourcePath.TODOS.getPath());
        Todo[] todos = response.getBody().as(Todo[].class);

        assertThat(todos).hasSize(20);
        assertThat(todos).allSatisfy(e -> assertThat(e.getUserId()).isEqualTo(userId));
    }

    @Test
    public void createNewTodo() {
        Todo newTodo = Todo.builder()
                .userId(1)
                .title("autem")
                .completed(true).build();

        Response response = createNewResource(newTodo, ResourcePath.TODOS.getPath());
        Todo todo = response.getBody().as(Todo.class);

        assertThat(todo.getId()).isEqualTo(201L);
    }

    @Test
    public void updatePTodo() {
        Todo updatedTodo = Todo.builder()
                .id(1)
                .userId(2)
                .title("autem")
                .completed(true).build();

        Response response = updateResource(updatedTodo, ResourcePath.TODOS.getPath());
        Todo todo = response.getBody().as(Todo.class);

        assertThat(todo.getId()).isEqualTo(updatedTodo.getId());
    }

    @Test
    public void patchTodo() {
        Todo updatedTodo = Todo.builder()
                .id(1)
                .title("autem").build();

        Response response = patchResource(updatedTodo, ResourcePath.TODOS.getPath());
        Todo todo = response.getBody().as(Todo.class);

        assertThat(todo.getId()).isEqualTo(updatedTodo.getId());
    }

    @Test
    public void deleteTodo() {
        checkDeleteResource(1, ResourcePath.TODOS.getPath());
    }
}
