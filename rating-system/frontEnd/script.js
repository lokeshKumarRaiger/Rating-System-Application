document.addEventListener("DOMContentLoaded", () => {
  fetch("http://localhost:8080/api/products/summary")
    .then(response => response.json())
    .then(products => {
      const productList = document.getElementById("product-list");
      products.forEach(product => {
        const div = document.createElement("div");
        div.className = "product-card";
        div.innerHTML = `
          <h3>${product.name}</h3>
          <p>${product.description}</p>
          <p><strong>Average Rating:</strong> ${product.averageRating}</p>

          <form onsubmit="submitReview(event, ${product.id})">
            <input type="text" name="username" placeholder="Your Username" required />
            <input type="number" name="rating" placeholder="Rating (1-5)" min="1" max="5" required />
            <input type="text" name="comment" placeholder="Your Comment (optional)" />
            <button type="submit">Submit Review</button>
          </form>
        `;
        productList.appendChild(div);
      });
    })
    .catch(error => {
      console.error("Error fetching products:", error);
      alert("Failed to load product data.");
    });
});

function submitReview(event, productId) {
  event.preventDefault();
  const form = event.target;
  const username = form.username.value;
  const rating = parseInt(form.rating.value);
  const comment = form.comment.value;

  fetch("http://localhost:8080/api/reviews", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      username,
      rating,
      comment,
      product: { id: productId }
    })
  })
  .then(res => res.text())
  .then(message => {
    alert(message);
    location.reload();
  })
  .catch(err => {
    alert("Something went wrong!");
    console.error(err);
  });
}
