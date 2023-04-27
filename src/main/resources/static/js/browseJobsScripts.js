// let jobsData;
// let searchedJobs;

document.addEventListener('DOMContentLoaded', async () => {
    const jobCardContainer = document.getElementById('job-card-container');
    const loader = document.getElementById('loader');


    async function fetchJobs() {
        const response = await fetch('/json');
        const jobs = await response.json();
        return jobs.results;
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

});


// async function getData() {
//     try {
//         const storedData = localStorage.getItem('browseJobsData');
//         // let data;
//
//         if (storedData) {
//             jobsData = JSON.parse(storedData);
//         } else {
//             const response = await fetch(`${jobKey}`);
//
//             if (!response.ok) {
//                 throw new Error(`HTTP error! status: ${response.status}`);
//             }
//             const apiData = await response.json();
//             jobsData = apiData.results;
//             localStorage.setItem('browseJobsData', JSON.stringify(jobsData));
//         }
//         searchedJobs = jobsData;
//         updateJobCards(jobsData);
//
//         console.log(jobsData);
//
//         // Hide the loading page and show the job cards container
//         document.getElementById('loader').style.display = 'none';
//         document.getElementById('job-card-container').style.display = 'block';
//
//     } catch (error) {
//         console.error('Error:', error);
//     }
// }
//
// function updateJobCards(data) {
//     const jobCardContainer = document.getElementById('job-card-container');
//     jobCardContainer.innerHTML = ''; // clear previous contents
//
//     // Create a job card for each job
//     data.forEach((job) => {
//         const card = createJobCard(job);
//         jobCardContainer.appendChild(card);
//     });
// }
//
// function searchJobs() {
//     const searchTerm = document.getElementById('search-term').value.toLowerCase();
//
//     if (!searchTerm) {
//         // If the search term is empty, show all jobs
//         updateJobCards(jobsData);
//         return;
//     }
//
//     const filteredJobs = jobsData.filter((job) => {
//
//         // Provide default values for any undefined properties
//         const title = job.title?.toLowerCase() || '';
//         const locationName = job.location?.display_name?.toLowerCase() || '';
//         const area = job.location?.area || [];
//         const country = area[0] || '';
//         const state = area[1] || '';
//         const county = area[2] || '';
//         const city = area[3] || '';
//         const companyName = job.company?.display_name?.toLowerCase() || '';
//         const description = job.description?.toLowerCase() || '';
//
//         // Filter by job title, location, or company name
//         return (
//             title.includes(searchTerm) ||
//             locationName.includes(searchTerm) ||
//             country.toLowerCase().includes(searchTerm) ||
//             state.toLowerCase().includes(searchTerm) ||
//             county.toLowerCase().includes(searchTerm) ||
//             city.toLowerCase().includes(searchTerm) ||
//             companyName.includes(searchTerm) ||
//             description.includes(searchTerm)
//         );
//     });
//
//     if (filteredJobs.length === 0) {
//         const noResultsMessage = document.createElement('p');
//         noResultsMessage.className = 'no-results-message text-center';
//         noResultsMessage.innerText = 'Sorry...No results found.';
//         const jobCardContainer = document.getElementById('job-card-container');
//         jobCardContainer.innerHTML = ''; // clear previous contents
//         jobCardContainer.appendChild(noResultsMessage);
//         return;
//     }
//
//
//     searchedJobs = filteredJobs;
//
//     updateJobCards(filteredJobs);
// }
//
// function filterJobs(filterId) {
//     let filteredJobs;
//     switch (filterId) {
//         case 'high-low':
//             filteredJobs = searchedJobs.slice().sort((a, b) => b.salary_max - a.salary_max);
//             break;
//         case 'low-high':
//             filteredJobs = searchedJobs.slice().sort((a, b) => a.salary_max - b.salary_max);
//             break;
//         case 'new-old':
//             filteredJobs = searchedJobs.slice().sort((a, b) => new Date(b.created) - new Date(a.created));
//             break;
//         case 'old-new':
//             filteredJobs = searchedJobs.slice().sort((a, b) => new Date(a.created) - new Date(b.created));
//             break;
//         case 'full-time':
//             filteredJobs = searchedJobs.filter(job => job.contract_time === "full_time");
//             break;
//         case 'part-time':
//             filteredJobs = searchedJobs.filter(job => job.contract_time === "part_time");
//             break;
//         case 'contract':
//             filteredJobs = searchedJobs.filter(job => job.contract_time === "contract");
//             break;
//         default:
//             filteredJobs = searchedJobs;
//     }
//
//     const jobCardContainer = document.getElementById('job-card-container');
//     jobCardContainer.innerHTML = ''; // clear previous contents
//
//     if (filteredJobs.length === 0) {
//         const noResultsMessage = document.createElement('p');
//         noResultsMessage.className = 'no-results-message text-center';
//         noResultsMessage.innerText = 'Sorry...No results found.';
//         jobCardContainer.appendChild(noResultsMessage);
//         return;
//     }
//
//     updateJobCards(filteredJobs);
// }
//
// // function getPostedAgo(date){
// //     let today = new Date;
// //     let thisDay = today.getDate();
// //     let dateCreated = date.getDate();
// //     let xDay = thisDay - dateCreated;
// //     if(xDay < 0){
// //         return 0;
// //     } else {
// //         return xDay
// //     }
// // }
//
// window.onload = function () {
//     getData();
//
//     const searchButton = document.querySelector('.search-btn');
//     searchButton.addEventListener('click', searchJobs);
//
//     const searchTermInput = document.getElementById('search-term');
//     searchTermInput.addEventListener('keypress', (event) => {
//         if (event.keyCode === 13) {
//             event.preventDefault(); // Prevent the form from submitting
//             searchJobs();
//         }
//     });
//
//     searchTermInput.addEventListener('input', (event) => {
//         if (!event.target.value.trim()) {
//             // If the search term is empty, show all jobs
//             searchedJobs = jobsData;
//             updateJobCards(jobsData);
//         }
//     });
//
//     const filterSelect = document.querySelector('.form-select select');
//     filterSelect.addEventListener('change', (event) => {
//         const filterId = event.target.value;
//         filterJobs(filterId);
//     });
// };


