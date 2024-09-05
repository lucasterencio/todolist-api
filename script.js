const sectionTask = document.querySelector(".task");
const inputAdd = document.querySelector(".input-add");
const btnEditar = document.querySelector(".btnEditar");
const btnExcluir = document.querySelector(".btnExcluir");
const btnCancelar = document.querySelector(".btnCancelar");
const btnAdicionar = document.querySelector(".btnAdicionar");
const inputsSelecionar = document.querySelector(".inputs-selecionar");

window.onload = async function () {
  const response = await fetch("http://localhost:8080/task/listar");
  if (response.status === 200) {
    const obj = await response.json();

    obj.map((item) => {
      let div = document.createElement("div");

      div.innerHTML = `<p>${item.description}</p> <button>Selecionar</button>`;

      const button = div.querySelector("button");
      button.classList.add("button");

      button.addEventListener("click", function (e) {
        e.preventDefault();

        clickSelect(item);
      });

      sectionTask.appendChild(div);
    });
  }
};

async function createTask() {
  if (inputAdd.value !== "") {
    await fetch("http://localhost:8080/task/criar", {
      method: "post",
      body: JSON.stringify({
        description: inputAdd.value,
      }),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    }).then((response) => {
      !response.ok
        ? response.json()
        : new Error("Erro na requisição: " + response.statusText);
    });

    limparFild();
  } else {
    alert("Campo vazio");
  }
}

function clickSelect(item) {
  inputAdd.value = item.description;
  inputsSelecionar.setAttribute("style", "display:flex");

  btnAdicionar.setAttribute("style", "display:none");

  btnExcluir.addEventListener("click", async function () {
    const id = item.id;
    const url = `http://localhost:8080/task/deletar/${id}`;

    await fetch(url, {
      method: "delete",
      "Content-Type": "application/json",
    }).then((response) => response.json());

    limparFild();
  });

  btnEditar.addEventListener("click", async function () {
    if (inputAdd.value !== "") {
      await fetch("http://localhost:8080/task/editar", {
        method: "put",
        body: JSON.stringify({
          id: item.id,
          description: inputAdd.value,
        }),
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
      }).then((response) => {
        !response.ok
          ? response.json()
          : new Error("Erro na requisição: " + response.statusText);
      });

      limparFild();
    } else {
      alert("Campo vazio");
    }
  });
}

btnCancelar.addEventListener("click", function () {
  inputsSelecionar.setAttribute("style", "display:none");
  btnAdicionar.setAttribute("style", "display:block");
  inputAdd.value = "";
});

function limparFild() {
  inputAdd.value = "";
  window.location.reload();
}


