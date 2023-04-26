const key = apiKey;

function createJobCard(job) {
    const card = document.createElement('div');
    card.className = 'card m-3 position-relative';

    const cardBody = document.createElement('div');
    cardBody.className = 'card-body d-flex flex-column justify-content-between';

    const cardTitle = document.createElement('h5');
    cardTitle.className = 'card-title';
    cardTitle.innerText = job.title;

    const cardSubtitle = document.createElement('h6');
    cardSubtitle.className = 'card-subtitle mb-2 text-muted';
    cardSubtitle.innerText = job.company.display_name;

    const cardJobDate = document.createElement('p');
    cardJobDate.className = 'cardJobDate';

// Create a new Date object from the "2023-04-06T18:38:10Z" string
    const jobDate = new Date(job.created);

// Extract the individual date components
    const month = jobDate.getMonth() + 1; // Add 1 to get the zero-based month index
    const day = jobDate.getDate();
    const year = jobDate.getFullYear();

// Construct the formatted date string
    const formattedDate = `${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}-${year}`;

    let today = new Date;
    let thisDay = today.getDate();
    let xDays = thisDay - day;

    cardJobDate.innerText = "Posted: " + xDays + " days ago";

    // cardJobDate.innerText = `Date Job Listed: ${formattedDate}`;

    const cardJobId = document.createElement('p');
    cardJobId.className = 'cardJobId hidden';

    const cardSalary = document.createElement('p');
    cardSalary.className = 'cardSalary';

    if (job.salary_min !== job.salary_max) {
        const formattedMinSalary = job.salary_min.toLocaleString('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        });
        const formattedMaxSalary = job.salary_max.toLocaleString('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        });
        cardSalary.innerText = `${formattedMinSalary} - ${formattedMaxSalary}`;
    } else {
        const formattedSalary = job.salary_max.toLocaleString('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 0,
            maximumFractionDigits: 0
        });
        cardSalary.innerText = `${formattedSalary}`;
    }

    const cardLocation = document.createElement('p');
    cardLocation.className = 'cardLocation';
    cardLocation.innerText = `${job.location.area[3]}, ${job.location.area[1]}`;

    const cardDescription = document.createElement('p');
    cardDescription.className = 'cardDescription';
    cardDescription.innerHTML = `<strong>Job description:</strong><br>${job.description}`;

    const viewDetailsLink = document.createElement('button');
    viewDetailsLink.className = 'btn view-details-btn text-center mb-2';
    viewDetailsLink.href = '#';
    viewDetailsLink.innerText = 'Preview Job';
    viewDetailsLink.addEventListener('click', () => {
        const modalTitle = document.getElementById('job-modal-title');
        const modalBody = document.getElementById('job-modal-body');

        modalTitle.innerText = job.title;

        let salaryElement = document.createElement('p');
        if (job.salary_min !== job.salary_max) {
            const formattedMinSalary = job.salary_min.toLocaleString('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            });
            const formattedMaxSalary = job.salary_max.toLocaleString('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            });
            salaryElement.innerHTML = `<strong>Salary Range:</strong> ${formattedMinSalary} - ${formattedMaxSalary}`;
        } else {
            const formattedSalary = job.salary_max.toLocaleString('en-US', {
                style: 'currency',
                currency: 'USD',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            });
            salaryElement.innerHTML = `<strong>Salary:</strong> ${formattedSalary}`;
        }

        modalBody.innerHTML = `
      <p><strong>Company:</strong> ${job.company.display_name}</p>
      <p><strong>Job ID:</strong> ${job.id}</p>
       ${salaryElement.outerHTML}
      <p><strong>Location:</strong> ${job.location.area[3]}, ${job.location.area[1]}</p>
      <p><strong>Description:</strong> ${job.description}</p>
    `;

        const jobModal = new bootstrap.Modal(document.getElementById('job-modal'), {
            backdrop: 'static',
            keyboard: false
        });
        jobModal.show();
    });

    const applyNowLink = document.createElement('a');
    applyNowLink.className = 'btn mb-2 text-white apply-now-btn';
    applyNowLink.target = '_blank';
    applyNowLink.href = job.redirect_url;
    applyNowLink.innerText = 'Apply Now';

    const cardContent = document.createElement('div');


    cardBody.appendChild(cardTitle);
    cardBody.appendChild(cardSubtitle);
    cardBody.appendChild(cardJobDate);
    // cardBody.appendChild(cardJobId);
    cardBody.appendChild(cardSalary);
    cardBody.appendChild(cardLocation);

    const buttonsWrapper = document.createElement('div'); // added wrapper for buttons
    buttonsWrapper.className = 'd-flex flex-column align-items-start justify-content-center'; // added class for centering
    buttonsWrapper.appendChild(viewDetailsLink);
    buttonsWrapper.appendChild(applyNowLink);

    cardBody.appendChild(cardContent);
    cardBody.appendChild(buttonsWrapper); // added wrapper for buttons

    card.appendChild(cardBody);

    return card;
}

async function fetchData() {
    try {
        let data = JSON.parse(localStorage.getItem('jobData'));

        if (!data) {
            const response = await fetch(`${key}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            data = await response.json();
            localStorage.setItem('jobData', JSON.stringify(data));
        }
        console.log(data);

// Update featured jobs
        updateFeaturedJobs(data);

        // Hide the loading page and show the job cards container
        document.getElementById('loader').style.display = 'none';
        document.getElementById('featured-jobs-container').style.display = 'block';



    } catch (error) {
        console.error('Error:', error);
    }
}

function updateFeaturedJobs(data) {
    const featuredJobsContainer = document.querySelector('.featured-jobs-container');
    featuredJobsContainer.innerHTML = '';

    let jobs = [];
    if (data.results && data.results.length > 0) {
        jobs = data.results;
    } else if (data.length > 0) {
        jobs = data;
    }

    // Sort jobs by salary in descending order and select the top 3
    const topSalaryJobs = jobs.sort((a, b) => b.salary_max - a.salary_max).slice(0, 3);

    topSalaryJobs.forEach(job => {
        const jobCard = createJobCard(job);
        const col = document.createElement('div');
        col.className = 'col-md-4';
        col.appendChild(jobCard);
        featuredJobsContainer.appendChild(col);
    });
}

window.onload = function () {
    fetchData();
};



