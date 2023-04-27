let jobsData;
let searchedJobs;

document.addEventListener('DOMContentLoaded', async () => {
    const jobCardContainer = document.getElementById('job-card-container');
    const loader = document.getElementById('loader');


    async function fetchJobs() {
        const response = await fetch('/json');
        const jobs = await response.json();
        jobsData = jobs.results
        return jobsData;
    }

    function createJobCard(job) {
        const card = document.createElement('div');
        card.className = 'card m-3';

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

        if (xDays < 0) {
            cardJobDate.innerText = "Posted: Unknown";
        } else {
            cardJobDate.innerText = "Posted: " + xDays + " days ago";
        }

        // cardJobDate.innerText = "Posted: " + getPostedAgo(formattedDate) + " days ago";

        // cardJobDate.innerText = `Date Job Listed: ${formattedDate}`;

        const cardJobId = document.createElement('p');
        cardJobId.className = 'cardJobId';
        cardJobId.innerText = "Job Id: " + job.id;

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
        // checking to see if the length of area array is 4 and if city or state is undefined or null
        if(job.location.area.length <= 3) {
            cardLocation.innerText = `Location: Not Provided`;
        } else if (job.location.area[1] === "undefined" || job.location.area[1] === null) {
            cardLocation.innerText = `Location: Not Provided`;
        } else if (job.location.area[3] === "undefined" || job.location.area[3] === null) {
            cardLocation.innerText = `${job.location.area[1]}`;
        } else {
            cardLocation.innerText = `${job.location.area[3]}, ${job.location.area[1]}`;
        }

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

        // const saveJobButton = document.createElement('button');
        // saveJobButton.className = 'btn save-job-btn mb-2';
        // saveJobButton.innerText = 'Save Job';
        // saveJobButton.addEventListener('click', () => {
        //     // Save the job to the user's saved jobs list
        //     alert(`Job ${job.id} saved!`);
        // });


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
        // buttonsWrapper.appendChild(saveJobButton);

        cardBody.appendChild(cardContent);
        cardBody.appendChild(buttonsWrapper); // added wrapper for buttons

        card.appendChild(cardBody);

        return card;
    }

    async function renderJobs() {
        const jobs = await fetchJobs();

        // Hide the loader
        loader.style.display = 'none';

        // Clear the job card container
        jobCardContainer.innerHTML = '';

        // Render the job cards
        jobs.forEach((job) => {
            const jobCard = createJobCard(job);
            jobCardContainer.appendChild(jobCard);
        });

    }

    await renderJobs();

    // Add event listener for the search button
    const searchButton = document.querySelector('.search-btn');
    searchButton.addEventListener('click', handleSearch);


    // Add event listener for the search input field
    const searchTermInput = document.getElementById('search-term');
    searchTermInput.addEventListener('input', handleSearch);
    searchTermInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            event.preventDefault();
            handleSearch();
        }
    });

    // Function to handle search functionality
    function handleSearch() {
        const searchTerm = searchTermInput.value.trim().toLowerCase();
        if (searchTerm === '') {
            renderJobs(); // call renderJobs() to display all jobs
            return;
        }
        searchedJobs = jobsData.filter((job) => {
            return (
                job.title.toLowerCase().includes(searchTerm) ||
                job.company.display_name.toLowerCase().includes(searchTerm) ||
                `${job.location.area[3]}, ${job.location.area[1]}`
                    .toLowerCase()
                    .includes(searchTerm)
            );
        });

        displaySearchedJobs();
    }

    // Function to display the searched jobs
    function displaySearchedJobs() {
        // Hide the loader
        loader.style.display = 'none';

        // Clear the job card container
        jobCardContainer.innerHTML = '';

        if (searchedJobs.length === 0) {
            // Create a message element
            const message = document.createElement('div');
            message.className = 'search-error-message text-center mt-5 col-12 col-md-8 offset-md-2';
            message.innerText = "Sorry...there were no results found";

            // Append the message element to the job card container
            jobCardContainer.appendChild(message);
        } else {
            // Render the job cards
            searchedJobs.forEach((job) => {
                const jobCard = createJobCard(job);
                jobCardContainer.appendChild(jobCard);
            });
        }
    }

    const filterSelect = document.getElementById('filter-select');
    filterSelect.addEventListener('change', handleFilter);

    function handleFilter() {
        const filterOption = filterSelect.value;

        const filteredJobs = jobsData.filter((job) => {
            switch (filterOption) {
                case 'full-time':
                    return job.contract_type === 'full_time';
                case 'part-time':
                    return job.contract_type === 'part_time';
                case 'contract':
                    return job.contract_type === 'contract';
                default:
                    return true;
            }
        });

        if (filterOption === 'high-low') {
            filteredJobs.sort((a, b) => b.salary_max - a.salary_max);
        } else if (filterOption === 'low-high') {
            filteredJobs.sort((a, b) => a.salary_max - b.salary_max);
        } else if (filterOption === 'new-old') {
            filteredJobs.sort((a, b) => new Date(b.created) - new Date(a.created));
        } else if (filterOption === 'old-new') {
            filteredJobs.sort((a, b) => new Date(a.created) - new Date(b.created));
        }

        displayFilteredJobs(filteredJobs);
    }

    function displayFilteredJobs(filteredJobs) {
        // Hide the loader
        loader.style.display = 'none';

        // Clear the job card container
        jobCardContainer.innerHTML = '';

        if (filteredJobs.length === 0) {
            // Create a message element
            const message = document.createElement('div');
            message.className = 'search-error-message text-center mt-5 col-12 col-md-8 offset-md-2';
            message.innerText = `Sorry...there are no results for the selected filter`;

            // Append the message element to the job card container
            jobCardContainer.appendChild(message);
        } else {
            // Render the job cards
            filteredJobs.forEach((job) => {
                const jobCard = createJobCard(job);
                jobCardContainer.appendChild(jobCard);
            });
        }
    }


});


